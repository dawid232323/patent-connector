# build files
FROM node:19-alpine as node
WORKDIR /app
COPY ./ ./
RUN npm install
RUN npm run build -- --configuration localProd

# move files to nginx image
FROM nginx:1.25-alpine
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=node /app/dist/frontend/browser /usr/share/nginx/html

