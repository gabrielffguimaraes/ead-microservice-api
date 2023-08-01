<li>Commands heroku cli in this service</li>

```
// criar o repositório remotamente no heroku
heroku create -a ead-ga-serviceregistry-prod --remote heroku-prod

// faz deploy (subtree)
git subtree push --prefix service-registry heroku prod:master

// ver logs
heroku logs --tail
```

