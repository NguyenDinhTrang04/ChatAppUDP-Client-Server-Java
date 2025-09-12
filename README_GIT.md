# Guide to Push Project to GitHub

## 1. Initialize repo (if not done)

```
git init
git add .
git commit -m "chore: initial project with structure"
```

## 2. Connect remote

```
git remote add origin https://github.com/<username>/<repo>.git
```

## 3. Push code

```
git push -u origin main
```

(If default branch is `master`, replace with `master`).

## 4. Tag version (optional)

```
git tag -a v1.0.0 -m "First stable release"
git push origin v1.0.0
```

## 5. Future updates

```
git add .
git commit -m "feat: ..."
git push
```

## 6. Pull latest changes

```
git pull
```
