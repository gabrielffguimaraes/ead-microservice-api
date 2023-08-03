heroku logs --tail

heroku git:remote -a ead-ga-authuser-prod

git push heroku-prod prod:master

heroku create -a ead-ga-authuser-prod --remoto heroku-prod

git subtree push --prefix service-registry heroku prod:master


git push heroku prod:master



