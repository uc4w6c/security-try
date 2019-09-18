FROM catatnight/postfix:latest
COPY ./main.cf /etc/postfix/main.cf
COPY ./transport /etc/postfix/transport
RUN sudo postmap /etc/postfix/transport
EXPOSE 25

