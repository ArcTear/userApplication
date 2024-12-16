# Kotlin Spring Boot Application on Minikube

This guide explains how to deploy a Kotlin Spring Boot application in Minikube and verify its functionality using an Ingress controller.

---

## Prerequisites

1. **Installed Tools**:
   - [Minikube](https://minikube.sigs.k8s.io/docs/start/)
   - [kubectl](https://kubernetes.io/docs/tasks/tools/)
   - [Docker](https://www.docker.com/)
   - [Colima](https://github.com/abiosoft/colima) (if using it for Docker)
2. **Resources**:
   - At least 2 CPUs and 4GB RAM allocated to Minikube.

---

## Steps to Run the Application

### 1. Start Minikube

Start Minikube with the necessary resources and addons:
```bash
minikube start --memory=4096 --cpus=2 --addons=ingress
```

---

### 2. Build and Load the Docker Image

#### a. Use Minikube’s Docker Environment
Ensure the Docker commands are executed in Minikube's Docker environment:
```bash
eval $(minikube docker-env)
```

#### b. Build the Docker Image
Build the application image:
```bash
docker build -t kotlin-app:latest .
```

#### c. Verify the Image
Ensure the image is listed in Minikube’s Docker environment:
```bash
docker images
```

---

### 3. Apply Kubernetes Manifests

Place all your Kubernetes manifests (e.g., deployment, service, ingress) in the `k8s/` folder.

Example directory structure:
```
k8s/
  ├── deployment.yaml
  ├── service.yaml
  ├── ingress.yaml
```

Apply all manifests at once:
```bash
kubectl apply -f k8s/
```

---

### 4. Verify the Deployment

#### a. Check Pods
Ensure the pods are running:
```bash
kubectl get pods
```

#### b. Check Services
Verify the services are created:
```bash
kubectl get svc
```

#### c. Check Ingress
Verify the Ingress is configured:
```bash
kubectl get ingress
```

Expected output:
```
NAME             CLASS    HOSTS           ADDRESS        PORTS   AGE
kotlin-ingress   <none>   kotlin.local    192.168.49.2   80      1m
```

---

### 5. Configure Host Mapping

Update your `/etc/hosts` file to map the Minikube IP to the hostname:
```bash
sudo nano /etc/hosts
```

Add the following line, replacing `<minikube-ip>` with the output of `minikube ip`:
```
127.0.0.1 kotlin.local
```

Save and exit the editor.

---

### 6. Verify the Application

#### a. Start the Minikube Tunnel
Run the Minikube tunnel to expose the Ingress service:
```bash
minikube tunnel
```

#### b. Test the Application
Access the application in your browser or with `curl`:
```bash
curl http://kotlin.local
```

---

## Troubleshooting

1. **Check Pod Logs**:
   ```bash
   kubectl logs -l app=kotlin-app
   ```

2. **Inspect Ingress**:
   ```bash
   kubectl describe ingress kotlin-ingress
   ```

3. **Verify Minikube Status**:
   ```bash
   minikube status
   ```

4. **Debug Events**:
   ```bash
   kubectl get events --sort-by='.metadata.creationTimestamp'
   ```

---

## Clean Up

To stop and clean up the Minikube cluster:
```bash
minikube delete
```

---

## Additional Notes

- Ensure Colima or other Docker environments do not conflict with Minikube's Docker daemon.
- Use `kubectl get all` to see all resources related to the application.

This completes the deployment of the Kotlin Spring Boot application in Minikube with Ingress.
