import * as RadixTabs from '@radix-ui/react-tabs'
import styled from 'styled-components'

export const Tabs = RadixTabs.Root

export const TabsList = styled(RadixTabs.List)`
  display: flex;
  border-bottom: 1px solid ${({ theme }) => theme.colors.gray[200]};
  gap: 0;
`

export const TabsTrigger = styled(RadixTabs.Trigger)`
  padding: ${({ theme }) => `${theme.spacing[2]} ${theme.spacing[4]}`};
  font-size: ${({ theme }) => theme.fontSize.sm};
  font-weight: ${({ theme }) => theme.fontWeight.medium};
  color: ${({ theme }) => theme.colors.gray[600]};
  border: none;
  border-bottom: 2px solid transparent;
  background: none;
  cursor: pointer;
  margin-bottom: -1px;
  transition: color 150ms ease, border-color 150ms ease;
  outline: none;

  &:hover {
    color: ${({ theme }) => theme.colors.gray[900]};
  }

  &[data-state='active'] {
    color: ${({ theme }) => theme.colors.blue[600]};
    border-bottom-color: ${({ theme }) => theme.colors.blue[600]};
  }

  &:focus-visible {
    box-shadow: 0 0 0 2px ${({ theme }) => theme.colors.blue[500]};
    border-radius: ${({ theme }) => theme.borderRadius.sm};
  }
`

export const TabsContent = styled(RadixTabs.Content)`
  outline: none;

  &:focus-visible {
    box-shadow: 0 0 0 2px ${({ theme }) => theme.colors.blue[500]};
    border-radius: ${({ theme }) => theme.borderRadius.md};
  }
`
