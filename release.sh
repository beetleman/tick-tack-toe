#!/usr/bin/env bash
RESURCES=./resources/public
DIST=./dist

rm $DIST -rf

lein clean || exit 1
lein cljsbuild once min || exit 1

cp $RESURCES/index.html $DIST/ || exit 1
cp $RESURCES/fonts $DIST/ -r || exit 1
cp $RESURCES/css $DIST/ -r || exit 1
