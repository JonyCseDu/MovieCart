#!/bin/bash

git fetch --all
git reset --hard origin/master

# note the SHA-1 of latest commit
#git checkout master
# reset your branch head to your previously detached commit
git reset --hard

git pull https://github.com/JonyCseDu/Hall-Video-Streaming.git
git config --global push.default matching
git config --global push.default simple

email="email"
name="name"

read -p "Email : " email
read -p "Name : " name

git config --global user.email "$email"
git config --global user.name "$name"
<<<<<<< HEAD

echo "DONE"

=======
>>>>>>> 0eb457166189b833250ee2a5e26ddc630b5359fc

echo "DONE"
