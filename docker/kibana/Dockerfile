FROM java_image

RUN useradd -d /home/esuser -m esuser
WORKDIR /home/esuser

ARG DEBIAN_FRONTEND=noninteractive
ENV KIBANA_VER 4.5.2

RUN \
 wget https://download.elastic.co/kibana/kibana/kibana-4.5.2-linux-x64.tar.gz && \
 tar xvzf kibana-${KIBANA_VER}-linux-x64.tar.gz && \
 rm -f kibana-${KIBANA_VER}-linux-x64.tar.gz && \
 mv kibana-${KIBANA_VER}-linux-x64 kibana && \
 chown -R esuser:esuser kibana

ADD kibana.yml kibana/config/kibana.yml

RUN kibana/bin/kibana plugin --install elastic/sense

ENV PATH /home/esuser/kibana/bin:$PATH

ENTRYPOINT kibana
