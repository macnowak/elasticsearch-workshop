FROM java_image

RUN useradd -d /home/esuser -m esuser

WORKDIR /home/esuser

ENV DEBIAN_FRONTEND noninteractive
ENV ES_VERSION 2.4.4

RUN \
 wget https://download.elasticsearch.org/elasticsearch/release/org/elasticsearch/distribution/tar/elasticsearch/$ES_VERSION/elasticsearch-$ES_VERSION.tar.gz && \
 tar xvzf elasticsearch-${ES_VERSION}.tar.gz && \
 rm -f elasticsearch-${ES_VERSION}.tar.gz && \
 mv elasticsearch-${ES_VERSION} elasticsearch && \
 chown -R esuser:esuser elasticsearch

# elasticsearch.yml and Dockerfile are on same location
ADD elasticsearch.yml elasticsearch/config/elasticsearch.yml

RUN elasticsearch/bin/plugin install royrusso/elasticsearch-hq/v2.0.3

RUN elasticsearch/bin/plugin install license

RUN elasticsearch/bin/plugin install mobz/elasticsearch-head

RUN elasticsearch/bin/plugin install lmenezes/elasticsearch-kopf/2.0

RUN elasticsearch/bin/plugin install analysis-icu

ADD start.sh start.sh
RUN chmod +x /home/esuser/start.sh

# add elasticsearch bin to PATH
ENV PATH /home/esuser:/home/esuser/elasticsearch/bin:$PATH

ENTRYPOINT start.sh
