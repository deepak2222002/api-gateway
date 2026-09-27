pipeline {

    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t api-gateway .'
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    docker stop api-gateway || true
                    docker rm api-gateway || true

                    docker run -d \
                    --name api-gateway \
                    --network backend_default \
                    --restart unless-stopped \
                    -p 8090:8443 \
                    -e AUTH_SERVICE_URL=https://localhost:8443 \
  					-e USER_SERVICE_URL=https://localhost:8443 \
                    -e KAFKA_BOOTSTRAP_SERVERS="kafka:9092" \
                    -e MAIL_PASSWORD="$MAIL_PASSWORD" \
                    api-gateway
                '''
            }
        }

    }
}