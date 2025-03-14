#!/bin/bash

HOOKS_DIR=".git/hooks"
SCRIPT_PATH="bin/script-pre-commit.sh"

# Make sure the hooks directory exists
mkdir -p $HOOKS_DIR

# Create the pre-commit hook that calls our script
cat > "$HOOKS_DIR/pre-commit" << 'EOF'
#!/bin/bash

# Path to the actual script
SCRIPT_PATH="$(git rev-parse --show-toplevel)/bin/script-pre-commit.sh"

# Check if the script exists
if [ -f "$SCRIPT_PATH" ]; then
    # Execute the script
    bash "$SCRIPT_PATH"
else
    echo "Error: Pre-commit script not found at $SCRIPT_PATH"
    exit 1
fi
EOF

# Make sure the hooks are executable
chmod +x "$HOOKS_DIR/pre-commit"
chmod +x "$SCRIPT_PATH"

echo "Git pre-commit hook installed successfully!"