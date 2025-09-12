#!/bin/bash
#cloud-config
# User-data script for Ubuntu 24.04 AWS VM
# Installs Jenkins and Terraform
set -e
apt-get update -y && apt-get upgrade -y
apt-get install -y curl unzip gnupg fontconfig openjdk-17-jre

# -------------------------------
# Install Jenkins
# -------------------------------
curl -fsSL https://pkg.jenkins.io/debian/jenkins.io-2023.key | tee \
  /usr/share/keyrings/jenkins-keyring.asc > /dev/null
echo deb [signed-by=/usr/share/keyrings/jenkins-keyring.asc] \
  https://pkg.jenkins.io/debian binary/ | \
  tee /etc/apt/sources.list.d/jenkins.list > /dev/null
apt-get update -y
apt-get install -y jenkins
systemctl enable jenkins
systemctl start jenkins

# -------------------------------
# Install Terraform
# -------------------------------
TERRAFORM_VERSION="1.9.5"
curl -fsSL https://releases.hashicorp.com/terraform/${TERRAFORM_VERSION}/terraform_${TERRAFORM_VERSION}_linux_amd64.zip -o terraform.zip
unzip terraform.zip
mv terraform /usr/local/bin/
rm terraform.zip
# Verify installs
java -version
jenkins --version || echo "Jenkins service installed (check systemctl)"
terraform -version

# -------------------------------
# Install CheckOv
# -------------------------------
sudo apt update && sudo apt install -y pipx python3-venv
python3 -m pipx ensurepath
# make the new PATH available in this session (if ensurepath modified your shell files)
export PATH="$HOME/.local/bin:$PATH"
pipx install checkov
checkov --version
which checkov
pipx list

# -------------------------------
# Install Snyk
# -------------------------------
sudo apt update && sudo apt install -y curl

curl -fsSL https://static.snyk.io/cli/latest/snyk-linux -o snyk
chmod +x snyk
sudo mv snyk /usr/local/bin/

# verify
snyk --version
