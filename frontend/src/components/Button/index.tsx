import styled, { css } from 'styled-components'

type Variant = 'primary' | 'secondary' | 'danger' | 'ghost'
type Size = 'sm' | 'md'

interface ButtonProps {
  $variant?: Variant
  $size?: Size
}

const variantStyles = {
  primary: css`
    background-color: ${({ theme }) => theme.colors.blue[600]};
    color: ${({ theme }) => theme.colors.white};
    border: 1px solid transparent;
    &:hover:not(:disabled) { background-color: ${({ theme }) => theme.colors.blue[700]}; }
  `,
  secondary: css`
    background-color: ${({ theme }) => theme.colors.white};
    color: ${({ theme }) => theme.colors.gray[700]};
    border: 1px solid ${({ theme }) => theme.colors.gray[300]};
    &:hover:not(:disabled) { background-color: ${({ theme }) => theme.colors.gray[50]}; }
  `,
  danger: css`
    background-color: ${({ theme }) => theme.colors.red[600]};
    color: ${({ theme }) => theme.colors.white};
    border: 1px solid transparent;
    &:hover:not(:disabled) { background-color: ${({ theme }) => theme.colors.red[700]}; }
  `,
  ghost: css`
    background-color: transparent;
    color: ${({ theme }) => theme.colors.gray[600]};
    border: 1px solid transparent;
    &:hover:not(:disabled) { background-color: ${({ theme }) => theme.colors.gray[100]}; }
  `,
}

const sizeStyles = {
  sm: css`
    padding: ${({ theme }) => `${theme.spacing[1]} ${theme.spacing[3]}`};
    font-size: ${({ theme }) => theme.fontSize.xs};
  `,
  md: css`
    padding: ${({ theme }) => `${theme.spacing[2]} ${theme.spacing[4]}`};
    font-size: ${({ theme }) => theme.fontSize.sm};
  `,
}

export const Button = styled.button<ButtonProps>`
  display: inline-flex;
  align-items: center;
  gap: ${({ theme }) => theme.spacing[2]};
  border-radius: ${({ theme }) => theme.borderRadius.md};
  font-weight: ${({ theme }) => theme.fontWeight.medium};
  cursor: pointer;
  transition: background-color 150ms ease, opacity 150ms ease;
  white-space: nowrap;

  ${({ $variant = 'primary' }) => variantStyles[$variant]}
  ${({ $size = 'md' }) => sizeStyles[$size]}

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }
`
