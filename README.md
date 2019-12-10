# cartisan



## 开发环境

### RabbitMQ 

```bash
docker pull rabbitmq:3.7.13-management
docker run -d \
    -p 5672:5672 -p 15672:15672 \
    -v ~/docker/rabbitmq:/var/rabbitmq/lib \
    --hostname cartisan-rabbitmq \
    -e RABBITMQ_DEFAULT_USER=rabbitmqadmin \
    -e RABBITMQ_DEFAULT_PASS=123456 \
    --name cartisan-rabbitmq rabbitmq:3.7.13-management 
```

### redis 

```bash
docker pull redis
docker run -d -p 6379:6379 -v ~/docker/redis:/data --restart=always --name cartisan-redis redis redis-server --appendonly yes --requirepass "123456"
```

### mongodb 

```bash
docker pull mongo
docker run -d -p 27017:27017 -v ~/docker/mongodb:/data/db -e MONGO_INITDB_ROOT_USERNAME=mongoadmin -e MONGO_INITDB_ROOT_PASSWORD=123456 --name cartisan-mongodb mongo
```

### elasticsearch 

```bash
docker pull elasticsearch:6.7.0
docker run -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" --name cartisan-elasticsearch elasticsearch:6.7.0
docker pull kibana:6.7.0
docker run -d -p 5601:5601 -v ~/docker/kibana/kibana.yml:/usr/share/kibana/config/kibana.yml --name cartisan-kibana kibana:6.7.0
```

### nginx

```bash
docker pull nginx
docker run -d -p 80:80 -v /Users/colin/Workspace/cartisan/cartisan-management-ui/dist:/usr/share/nginx/html --name cartisanNginx nginx
```

### mysql

```bash
docker pull mysql:8.0
docker run --name cartisan-mysql -p 3306:3306 --restart=always -e MYSQL_ROOT_PASSWORD=truth -v /usr/local/docker/mysql/data:/var/lib/mysql/ -d mysql
```

### jenkins

```bash
sudo chown -R 1000 /usr/local/jenkins
docker pull jenkins/jenkins
docker run -d -p 80:8080 -p 50000:50000 -v /usr/local/docker/jenkins:/var/jenkins_home -v /etc/localtime:/etc/localtime --name jenkins docker.io/jenkins/jenkins
docker exec jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

## 端口规划

### Web 应用

| 名称 | 端口 |
| --- | --- |
| management-ui | 8088 |

### 服务

| 名称 | 端口 |
| --- | --- |
| management | 8081 |
| system | 8082 |
| base | 9001 |
| goods | 9002 |
| huiduoduo | 9003 |

### 基础框架

| 名称 | 端口 |
| --- | --- |
| eureka | 8761 |
| gateway | 8889 |
| config | 8888 |
| hystrix-dashboard | 8887 |
| zipkin | 9411 |
