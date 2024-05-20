FROM node as build
WORKDIR /opt/app
COPY package*.json ./

RUN npm install
COPY . .

RUN npm run build -- --configuration production

# RUN ls -la /opt/app/dist
# RUN npm install -g http-server
# EXPOSE 8080

# CMD ["http-server", "/opt/app/dist/webshop-app"]
FROM nginx
COPY nginx_config/nginx.conf /etc/nginx/nginx.conf
# Copy the built Angular app from the previous stage
COPY --from=build /opt/app/dist/webshop-app /usr/share/nginx/html

# Copy custom Nginx configuration (optional)
# COPY nginx.conf /etc/nginx/nginx.conf

# Expose the port Nginx will serve on
EXPOSE 8080

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]