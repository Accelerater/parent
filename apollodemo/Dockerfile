FROM java:8

LABEL maintainer "hao.cheng@daocloud.io"


WORKDIR root/myapp/
ADD target/apollodemo-0.0.1-SNAPSHOT.jar apollodemo-test.jar
# add skywalking-agent.tar.gz --> agent dir.
ADD http://nexus.mschina.io/nexus/service/local/repositories/labs/content/io/daocloud/dmp/skywalking/agent/agent-5.0.0-ga/dmp-1.2/agent-5.0.0-ga-dmp-1.2.gz agent.gz


RUN  ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime  \
    && echo "Asia/Shanghai" > /etc/timezone \
    && tar -xvf agent.gz


ENTRYPOINT java -javaagent:agent/skywalking-agent.jar -jar \
$JAVA_OPTS_SW \
$JAVA_OPTS apollodemo-test.jar
