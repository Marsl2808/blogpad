FROM open-liberty:kernel-slim-java11-openj9
# full-java11-openj9

# Add server configuration
COPY --chown=1001:0  ./src/main/liberty/config/server.xml /config/
# add additional Liberty features
RUN features.sh

# Add the application
COPY --chown=1001:0  ./target/blogpad-backend.war /config/dropins/
# This script will add the requested server configurations, apply any interim fixes and populate caches to optimize runtime.
RUN configure.sh