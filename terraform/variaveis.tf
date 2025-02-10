variable "region" {
  description = "Região da AWS"
  type        = string
  default     = "us-east-1"
}

variable "cluster_name" {
  description = "Nome do cluster EKS"
  type        = string
  default = "restaurante-cluster2"
}

variable "mysql_user_produto" {
  description = "MySQL User para o serviço de produto"
  type        = string
  default     = "produto_user"
}

variable "mysql_password_produto" {
  description = "MySQL Password para o serviço de produto"
  type        = string
  default     = "produto_user_pass"
}

variable "mysql_database_produto" {
  description = "MySQL Database para o serviço de produto"
  type        = string
  default     = "produto_db"
}