FROM node:19.0-alpine3.15

COPY package.json app/package.json
COPY index.js app/index.js

WORKDIR app

ENTRYPOINT ["npm", "start"]