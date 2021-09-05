package com.bong.was;


import com.bong.was.util.PropertiesUtil;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cybaek on 15. 5. 22..
 */
public class HttpServer {
    private static Logger logger = LoggerFactory.getLogger(RequestProcessor.class);
    private static final int NUM_THREADS = 50;
    private static final String INDEX_FILE = "index.html";
    private final File rootDirectory;
    private final int port;

    public HttpServer(File rootDirectory, int port) throws IOException {
        if (!rootDirectory.isDirectory()) {
            throw new IOException(rootDirectory + " does not exist as a directory");
        }
        this.rootDirectory = rootDirectory;
        this.port = port;
    }

    public static void main(String[] args) {
        File docroot;
        try {
            String resource = ClassLoader.getSystemClassLoader().getResource("templates").getPath();
            logger.info("resource:{}", resource);
            String absolutePath = new File("").getAbsolutePath();
            logger.info("path:{}", absolutePath);
            docroot = new File(absolutePath + "/classes/templates");
        } catch (ArrayIndexOutOfBoundsException ex) {
            logger.debug("Usage: java JHTTP docroot port");
            return;
        }
        // set the port to listen on
        int port;
        try {
            port = PropertiesUtil.getPort();
            if (port <= 0 || port > 65535) port = 80;
        } catch (RuntimeException ex) {
            port = 80;
        }
        try {
            HttpServer webserver = new HttpServer(docroot, port);
            webserver.start();
        } catch (IOException ex) {
            logger.debug("Server could not start", ex);
        }
    }

    public void start() throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Document Root: " + rootDirectory);
            while (true) {
                try {
                    Socket request = server.accept();
                    Runnable r = new RequestProcessor(rootDirectory, INDEX_FILE, request);
                    pool.submit(r);
                } catch (IOException ex) {
                    logger.debug("Error accepting connection", ex);
                }
            }
        }
    }

}