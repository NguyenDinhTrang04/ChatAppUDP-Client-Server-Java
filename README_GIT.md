# Hướng dẫn đẩy dự án lên GitHub

## 1. Khởi tạo repo (nếu chưa)

```
git init
git add .
git commit -m "chore: initial project with structure"
```

## 2. Kết nối remote

```
git remote add origin https://github.com/<username>/<repo>.git
```

## 3. Đẩy code

```
git push -u origin main
```

(Nếu nhánh mặc định là `master` thì thay bằng `master`).

## 4. Tag phiên bản (tuỳ chọn)

```
git tag -a v1.0.0 -m "First stable release"
git push origin v1.0.0
```

## 5. Cập nhật sau này

```
git add .
git commit -m "feat: ..."
git push
```

## 6. Pull thay đổi mới nhất

```
git pull
```
