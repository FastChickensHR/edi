#!/bin/bash
echo "Running license:format pre-commit hook..."

# Store the files that were changed
STAGED_FILES=$(git diff --cached --name-only)

# Run the license format command
mvn license:format

# If the command failed, abort the commit
if [ $? -ne 0 ]; then
  echo "license:format failed, aborting commit"
  exit 1
fi

# Re-add the files that were modified by the license formatter
for FILE in $STAGED_FILES; do
  if [ -f "$FILE" ]; then
    git add "$FILE"
  fi
done

echo "License headers checked and formatted successfully."
exit 0