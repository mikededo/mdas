FROM nginx:1.23.2

COPY ./index.html /usr/share/nginx/html/index.html
EXPOSE 80

# añadimos el healthcheck
HEALTHCHECK --interval=45s \
  --timeout=5s \
  --start-period=5s \
  --retries=2 \
  CMD curl -f http://localhost:80/index.html || exit 1
