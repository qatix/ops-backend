#!/usr/bin/env bash

#clean built code
mvn clean

#到上级目录
cd ..

old_pkg="com.quasar.backend"
new_pkg="com.company.product"

old_pkg_dir=${old_pkg//'.'/'/'}
new_pkg_dir=${new_pkg//'.'/'/'}

#计算原package和新package的目录不同的最上层
old_pkg_arr=(${old_pkg//./ })
new_pkg_arr=(${new_pkg//./ })
old_len=${#old_pkg_arr[@]}
new_len=${#new_pkg_arr[@]}

min_len=$(($old_len > $new_len ? $new_len : $old_len))
diff_pkg_dir = ''
for i in $(seq 1 $min_len)
do
    j=$(($i-1))
    diff_pkg_dir=$diff_pkg_dir/${old_pkg_arr[$j]}
    if [ ${new_pkg_arr[$j]} != ${old_pkg_arr[$j]} ]
    then
        echo $diff_pkg_dir
        break
    fi
done

#move code to new dir
parent_dirs=("admin/src/main/java" "api/src/main/java" "common/src/main/java" "generator/src/main/java" "admin/src/test/java")
for parent_dir in ${parent_dirs[*]}
do
    old_dir=$parent_dir"/"$old_pkg_dir
    new_dir=$parent_dir"/"$new_pkg_dir
    mkdir -p $new_dir
    mv $old_dir/* $new_dir/
    echo "rm -rf $parent_dir$diff_pkg_dir"
    rm -rf $parent_dir$diff_pkg_dir
done

find . -type f -name "*.java" -print0 | xargs -0 sed -i '' -e "s/$old_pkg/$new_pkg/g"
find . -type f -name "*.xml" -print0 | xargs -0 sed -i '' -e "s/$old_pkg/$new_pkg/g"
find . -type f -name "*.yml" -print0 | xargs -0 sed -i '' -e "s/$old_pkg/$new_pkg/g"
find . -type f -name "*.properties" -print0 | xargs -0 sed -i '' -e "s/$old_pkg/$new_pkg/g"
find . -type f -name "*.vm" -print0 | xargs -0 sed -i '' -e "s/$old_pkg/$new_pkg/g"
echo "replace pkg done"


