# Arquivo main.tf

# Definição do provider Azure
provider "azurerm" {
  features {}
}

# Recurso de grupo de recursos
resource "azurerm_resource_group" "ptg2_resources" {
  name     = "ptg2-resources"
  location = "East US"
}

# Recurso MongoDB
resource "azurerm_cosmosdb_account" "ptg2_mongodb" {
  name                = "ptg2-mongodb"
  location            = azurerm_resource_group.ptg2_resources.location
  resource_group_name = azurerm_resource_group.ptg2_resources.name
  offer_type          = "Standard"
  kind                = "MongoDB"
}

# Recurso RabbitMQ
resource "azurerm_linux_virtual_machine" "ptg2_rabbitmq" {
  name                = "ptg2-rabbitmq"
  resource_group_name = azurerm_resource_group.ptg2_resources.name
  location            = azurerm_resource_group.ptg2_resources.location
  size                = "Standard_DS2_v2"
  admin_username      = "adminuser"
  network_interface_ids = [
    azurerm_network_interface.ptg2_example.id,
  ]
}

resource "azurerm_network_interface" "ptg2_nic" {
  name                = "ptg2-nic"
  location            = azurerm_resource_group.ptg2_resources.location
  resource_group_name = azurerm_resource_group.ptg2_resources.name

  ip_configuration {
    name                          = "internal"
    subnet_id                     = azurerm_subnet.ptg2_internal.id
    private_ip_address_allocation = "Dynamic"
  }
}

resource "azurerm_subnet" "ptg2_internal" {
  name                 = "ptg2-internal"
  resource_group_name  = azurerm_resource_group.ptg2_resources.name
  virtual_network_name = azurerm_virtual_network.ptg2_network.name
  address_prefixes     = ["10.0.1.0/24"]
}

resource "azurerm_virtual_network" "ptg2_network" {
  name                = "ptg2-network"
  resource_group_name = azurerm_resource_group.ptg2_resources.name
  location            = azurerm_resource_group.ptg2_resources.location
  address_space       = ["10.0.0.0/16"]
}

