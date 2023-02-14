# Camunda

## Deployment

### localhost

* Container runtime using [colima](https://github.com/abiosoft/colima):

```shell
brew install colima
brew install docker

colima start default --kubernetes --cpu 3 --memory 8 --disk 50
colima stop
colima start default

colima delete default
```

* Install Camunda helm chart based on the Camunda [Local Kubernetes Cluster](https://docs.camunda.io/docs/self-managed/platform-deployment/helm-kubernetes/guides/local-kubernetes-cluster/) instructions:

```shell
helm repo add camunda https://helm.camunda.io
helm repo update
```

* Download the [camunda-platform-core-kind-values](https://github.com/camunda/camunda-platform-helm/blob/main/kind/camunda-platform-core-kind-values.yaml) 
YAML file and remove/comment out the `elasticsearch -> volumeClaimTemplate -> storageClassName: "standard"`
and run:

```shell
helm install dev camunda/camunda-platform \
    -f camunda-platform-core-kind-values.yaml

helm upgrade dev camunda/camunda-platform \
    -f camunda-platform-core-kind-values.yaml    
```

* Wait until all pods are up and running
* Accessing the Camunda components without Ingress (TODO: add Ingress) as explained in this [link](https://docs.camunda.io/docs/self-managed/platform-deployment/helm-kubernetes/guides/accessing-components-without-ingress/)

```shell
# Operate
kubectl port-forward svc/dev-operate 8081:80

# Tasklist
kubectl port-forward svc/dev-tasklist 8082:80

# Zeebe
kubectl port-forward svc/dev-zeebe-gateway 26500:26500
```

Test access to the Zeebe Cluster

```shell
# Use a gRPC library 
brew install grpcurl 
# Download a proto buffer file 
curl -sSL https://raw.githubusercontent.com/camunda-cloud/zeebe/develop/gateway-protocol/src/main/proto/gateway.proto > gateway.proto
# Test
grpcurl -proto gateway.proto $ADDRESS gateway_protocol.Gateway.Topology
```

### exporters

Deploy Kibana for easy access to the Elasticsearch exporter data:

```shell
kubectl apply -f kibana.yaml

kubectl port-forward svc/kibana 5601:5601
```