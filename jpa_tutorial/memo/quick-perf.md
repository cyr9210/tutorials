# Test

## Quick Perf

- https://github.com/quick-perf/quickperf
- 일부 성능 관련 속성을 빠르게 평가하고 개선하기 위한 Java용 테스트 라이브러리
- Query 횟수를 테스트 할 수 있다.
    - JPA N+1 문제에 대해 테스트에서 발견할 수 있다. (https://github.com/quick-perf/doc/wiki/sql-annotations)

### 사용법

- 의존성 (Junit 5.6.0 이상의 버전이 필요하다.)

  ```xml
  <dependencyManagement>
          <dependencies>
              <dependency>
                  <groupId>org.junit</groupId>
                  <artifactId>junit-bom</artifactId>
                  <version>5.7.0</version>
                  <type>pom</type>
                  <scope>import</scope>
              </dependency>
              <dependency>
                  <groupId>org.quickperf</groupId>
                  <artifactId>quick-perf-bom</artifactId>
                  <version>1.0.1</version>
                  <type>pom</type>
                  <scope>import</scope>
              </dependency>
          </dependencies>
   </dependencyManagement>
  ```

    - QuickPerfBOM 파일을 사용할 수 있다.

    - Junit5

      ```xml
       <dependency>
          <groupId>org.junit.jupiter</groupId>
          <artifactId>junit-jupiter-engine</artifactId>
          <scope>test</scope>
        </dependency>
        <dependency>
           <groupId>org.junit.platform</groupId>
           <artifactId>junit-platform-launcher</artifactId>
           <scope>test</scope>
        </dependency>
      ```

        - `java.lang.NoClassDefFoundError: org/junit/platform/launcher/core/LauncherDiscoveryRequestBuilder`, then the `junit-platform-launcher` 에러가 발생한다면, `junit-platform-launcher` 가 누락된것이다.

    - QuickPerf

      ```xml
       <dependency>
           <groupId>org.quickperf</groupId>
           <artifactId>quick-perf-junit5</artifactId>
           <scope>test</scope>
       </dependency>
      ```

- Test

    - `@QuickPerfTest` 를 추가한다.

    - Junit5는 자동으로 QuickPerf를 확장 등록할 수 있다.

        - `src/test/resources/junit-platform.properties` 파일을 추가한다.

        - 아래 내용을 해당 파일에 추가한다.

          ```properties
          junit.jupiter.extensions.autodetection.enabled=true
          ```

    - `@ExpectSelect(2)` 쿼리 횟수를 검증 할 수 있다.

        - `@Import(QuickPerfSqlConfig.class)` 이 요구된다.

    - 여러가지 설정이 있다.

        - 글로벌 설정

          ```java
          public class GlobalQuickPerfConfiguration implements SpecifiableGlobalAnnotations {
          
            @Override
            public Collection<Annotation> specifyAnnotationsAppliedOnEachTest() {
              return Arrays.asList(
                  // Can reveal some N+1 selects
                  // https://blog.jooq.org/2017/12/18/the-cost-of-jdbc-server-roundtrips/
                  disableSameSelectTypesWithDifferentParamValues(),
          
                  // Sometimes, JDBC batching can be disabled:
                  // https://abramsm.wordpress.com/2008/04/23/hibernate-batch-processing-why-you-may-not-be-using-it-even-if-you-think-you-are/
                  // https://stackoverflow.com/questions/27697810/hibernate-disabled-insert-batching-when-using-an-identity-identifier
                  expectJdbcBatching(),
          
                  // https://use-the-index-luke.com/sql/where-clause/searching-for-ranges/like-performance-tuning
                  disableLikeWithLeadingWildcard(),
                  disableSameSelects(),
          
                  // Not relevant with an in-memory database used for testing purpose
                  expectMaxQueryExecutionTime(30),
                  disableStatements(),
                  disableQueriesWithoutBindParameters()
              );
            }
          }
          ```



### 주의사항

- `@BeforeAfter` 등에서 flush 하여 쿼리가 나가는것은 검증되지 않는다.