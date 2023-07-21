#!/bin/bash

# Project directories
project1_dir="./service-registry"
project2_dir="./config-server"
project3_dir="./api-gateway"
project4_dir="./authuser"
project5_dir="./course"

# Function to run Maven for a project in the background
run_maven_spring_boot_background() {
  local project_dir=$1
  echo "Running 'mvn spring-boot:run' for project in directory: $project_dir"
  cd "$project_dir" || exit
  mvn spring-boot:run
}


# Execute Maven for each project in the background
run_maven_spring_boot_background "$project1_dir" &
run_maven_spring_boot_background "$project2_dir" &
run_maven_spring_boot_background "$project3_dir" &

sleep 30

run_maven_spring_boot_background "$project4_dir" &
run_maven_spring_boot_background "$project5_dir" &
