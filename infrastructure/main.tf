# Terraform Docker Provider for localhost Docker Engine
terraform {
  required_providers {
    docker = {
      source = "kreuzwerker/docker"
      version = "3.0.2"
    }
  }
}

provider "docker" {
  host = "unix:///var/run/docker.sock"
}

resource "docker_container" "postgres" {
  image = "postgres:15"
  name  = "postgres"
  ports {
    internal = 5432
    external = 5432
  }
  env = ["POSTGRES_DB=order", "POSTGRES_USER=order", "POSTGRES_PASSWORD=order"]
}