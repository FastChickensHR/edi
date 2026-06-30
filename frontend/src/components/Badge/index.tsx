import styled, { css } from 'styled-components'

type BadgeVariant = 'inbound' | 'outbound' | 'default'

interface BadgeProps {
  $variant?: BadgeVariant
}

const variantStyles = {
  inbound: css`
    background-color: ${({ theme }) => theme.colors.blue[50]};
    color: ${({ theme }) => theme.colors.blue[700]};
  `,
  outbound: css`
    background-color: ${({ theme }) => theme.colors.green[50]};
    color: ${({ theme }) => theme.colors.green[700]};
  `,
  default: css`
    background-color: ${({ theme }) => theme.colors.gray[100]};
    color: ${({ theme }) => theme.colors.gray[700]};
  `,
}

export const Badge = styled.span<BadgeProps>`
  display: inline-flex;
  align-items: center;
  padding: ${({ theme }) => `${theme.spacing[0.5]} ${theme.spacing[2]}`};
  border-radius: ${({ theme }) => theme.borderRadius.full};
  font-size: ${({ theme }) => theme.fontSize.xs};
  font-weight: ${({ theme }) => theme.fontWeight.medium};
  white-space: nowrap;

  ${({ $variant = 'default' }) => variantStyles[$variant]}
`
