# JOB_NAME is the name of the project of this build. This is the name you gave your job. It is set up by Jenkins
COMPOSE_ID=${JOB_NAME:-local}
echo $COMPOSE_ID

# Remove Previous Stack
docker-compose -p $COMPOSE_ID rm -f

# Starting new stack environment
docker-compose -f docker-compose-api.yaml up -p $COMPOSE_ID -d --no-color --build
docker-compose -f docker-compose-api.yaml run -p $COMPOSE_ID -e TYPE="@API" api-test-service
