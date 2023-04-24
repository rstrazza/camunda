# Camunda

This monorepo contains applications to simulate different aspects of Camunda capabilities.

- `hello-world`: the name says it all
- `loan-originations`: the main Camunda application containing the BPMN and DMN
- `credit-app`: the external application demonstrating Camunda integrations

## Deployment

### Running Camunda on localhost

* Container runtime using [colima](https://github.com/abiosoft/colima):

*skip this step if you already have a kubernetes runtime available, such as Docker or Rancher Desktop*

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

* deploy camunda:

*alternatively, download the [camunda-platform-core-kind-values](https://github.com/camunda/camunda-platform-helm/blob/main/kind/camunda-platform-core-kind-values.yaml)
YAML file and remove/comment out the `elasticsearch -> volumeClaimTemplate -> storageClassName: "standard"`*

```shell
helm install dev camunda/camunda-platform \
    -f k8s/local/camunda-platform-core-kind-values.yaml

helm upgrade dev camunda/camunda-platform \
    -f k8s/local/camunda-platform-core-kind-values.yaml    
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

Web Browser based services:

* Operate: http://localhost:8081/
* Tasklist: http://localhost:8082/

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
kubectl apply -f k8s/local/kibana.yaml

kubectl port-forward svc/kibana 5601:5601
```

### Running the sample applications on localhost

* Start `credit-app` and `loan-originations`

## Links

* [zeebe-chaos](https://zeebe-io.github.io/zeebe-chaos/)
* [Zeebe.io — a horizontally scalable distributed workflow engine](https://blog.bernd-ruecker.com/zeebe-io-a-horizontally-scalable-distributed-workflow-engine-45788a90d549)