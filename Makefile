build:
	docker build -t fsp/auth .
run: build
	docker run -p 10000:8080 fsp/auth
