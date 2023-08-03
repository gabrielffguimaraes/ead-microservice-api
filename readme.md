heroku logs --tail

git push heroku-prod prod:master

heroku create -a ead-ga-configserver-prod --remoto heroku-prod

git subtree push --prefix service-registry heroku prod:master






