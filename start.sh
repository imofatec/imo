docker-compose down
docker build -t backend-imo:latest ./backend
docker build -t frontend-imo:latest ./frontend
docker-compose up --build --force-recreate --remove-orphans
