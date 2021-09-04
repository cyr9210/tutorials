package com.bong.was;

import com.bong.was.http_request.HttpRequestImpl;
import com.bong.was.http_response.HttpResponseImpl;
import com.bong.was.properties.Properties.HostInfo;
import com.bong.was.servlet.SimpleServletImpl;
import com.bong.was.util.PropertiesUtil;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class RequestProcessor implements Runnable {

    private final static Logger logger = Logger.getLogger(RequestProcessor.class.getCanonicalName());
    private static final String HOST = "host";
    private File rootDirectory;
    private String indexFileName;
    private Socket connection;

    public RequestProcessor(File rootDirectory, String indexFileName, Socket connection) {
        if (rootDirectory.isFile()) {
            throw new IllegalArgumentException("rootDirectory must be a directory, not a file");
        }

        try {
            rootDirectory = rootDirectory.getCanonicalFile();
        } catch (IOException ex) {
            //
        }
        this.rootDirectory = rootDirectory;
        if (indexFileName != null) {
            this.indexFileName = indexFileName;
        }
        this.connection = connection;
    }

    @Override
    public void run() {
        String root = rootDirectory.getPath();
        try {
            OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
            Writer out = new OutputStreamWriter(raw);

            String request = getRequestString();
            logger.info(connection.getRemoteSocketAddress() + " " + request);
            String[] requestElements = request.split("(\r\n)+");

            Map<String, String> headers = Arrays.stream(requestElements)
                .filter(element -> element.contains(":"))
                .map(element -> element.split(":"))
                .collect(Collectors.toMap(array -> array[0].trim(), array -> array[1].trim()));

            String requestLine = requestElements[0];
            String[] tokens = requestLine.split("\\s+");
            String method = tokens[0];
            String path = tokens[1];
            String[] split = path.split("\\?");
            String fileName = split[0];
            Map<String, String> parameters = new HashMap<>();
            if (split.length > 1){
                parameters = Arrays.stream(split[1].split("&"))
                    .map(parameter -> parameter.split("="))
                    .collect(Collectors.toMap(array -> array[0], array -> array[1]));
            }


            HttpRequestImpl httpRequest = new HttpRequestImpl(headers, parameters, method, fileName);
            String host = httpRequest.getHostHeader();
            HostInfo hostInfo = PropertiesUtil.getHostInfo(host);

            String hostRoot = root + "/" + hostInfo.getName();
            HttpResponseImpl httpResponse = new HttpResponseImpl(raw, out, hostRoot, httpRequest, hostInfo.getPageInfo());
            SimpleServletImpl servlet = new SimpleServletImpl(hostInfo);
            servlet.service(httpRequest, httpResponse);
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Error talking to " + connection.getRemoteSocketAddress(), ex);
        } finally {
            try {
                connection.close();
            } catch (IOException ex) {
            }
        }
    }

    private String getRequestString() throws IOException {
        Reader in = new InputStreamReader(new BufferedInputStream(connection.getInputStream()), "UTF-8");
        StringBuilder requestBuilder = new StringBuilder();
        while (in.ready()) {
            requestBuilder.append((char) in.read());
        }
        String request = requestBuilder.toString();
        return request;
    }

}