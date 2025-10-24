# Nonoichi Stamp
## 概要
＠このアプリがどういうアプリなのか、などの説明を書く＠

# GitHub の使い方
## 作業を始める前に

```bash
git status # ターミナルで打つこと
```

をしてください。

### main ブランチにいる場合
これによって、 `On Branch main` と出力された場合、作業部屋が異なるため、作業を中断してください。

また、この場合、作業を中断したうえで、変更ファイルを元に戻してください。

次に、このコマンドを打ってください。

```bash
git pull origin main # ターミナルで打つこと
```

次に、このコマンドを打ってから作業を開始してください。
※`[画面または機能名を英語で]`の部分は任意の言葉に変更してください。

```bash
git checkout -b feature/[画面または機能名を英語で]
# 例： git checkout -b feature/login
# 例： git checkout -b feature/logout
# 例： git checkout -b feature/add-point-counter
# 例： git checkout -b feature/make-map-view
```

これによって、作業前の準備は終了です。

### main ブランチにいない場合
`main`ブランチにいない場合は、現在の作業を続けてください。

## 作業中にやるべきこと
ひとつの画面あるいはひとつの機能が終わった時点で次のコマンドを打ってください。

```bash
# １行ずつ実行してください。
git add .

git commit -m "[任意のメッセージ]" # [任意のメッセージ]には、変更内容を書いてください。
# 例： git commit -m "ログイン画面の実装"

git branch

git push origin [ブランチ名] # git branch を実行したときに緑色で表示された文字を[ブランチ名]に入れてください。
# 例： git push origin feature/login
```

## 以上の作業を終了したら

これらの作業を終了したら、次のコマンドを実行してください。

```bash
git checkout main
```

このあとは [作業を始める前に](#作業を始める前に) に戻り、作業を進めてください。