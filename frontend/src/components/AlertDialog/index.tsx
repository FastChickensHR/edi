import * as RadixAlertDialog from '@radix-ui/react-alert-dialog'
import styled, { keyframes } from 'styled-components'
import { Button } from '@/components/Button'
import type { ReactNode } from 'react'

interface AlertDialogProps {
  open: boolean
  onOpenChange: (open: boolean) => void
  title: string
  description: string
  confirmLabel?: string
  onConfirm: () => void
  children?: ReactNode
}

const overlayShow = keyframes`
  from { opacity: 0; }
  to { opacity: 1; }
`

const contentShow = keyframes`
  from { opacity: 0; transform: translate(-50%, -48%) scale(0.96); }
  to { opacity: 1; transform: translate(-50%, -50%) scale(1); }
`

const Overlay = styled(RadixAlertDialog.Overlay)`
  background-color: rgba(0, 0, 0, 0.45);
  position: fixed;
  inset: 0;
  animation: ${overlayShow} 150ms ease;
  z-index: 40;
`

const Content = styled(RadixAlertDialog.Content)`
  background-color: ${({ theme }) => theme.colors.white};
  border-radius: ${({ theme }) => theme.borderRadius.lg};
  box-shadow: ${({ theme }) => theme.boxShadow.xl};
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 90vw;
  max-width: 28rem;
  padding: ${({ theme }) => theme.spacing[6]};
  animation: ${contentShow} 150ms ease;
  z-index: 50;

  &:focus {
    outline: none;
  }
`

const Title = styled(RadixAlertDialog.Title)`
  font-size: ${({ theme }) => theme.fontSize.lg};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.gray[900]};
  margin: 0 0 ${({ theme }) => theme.spacing[2]};
`

const Description = styled(RadixAlertDialog.Description)`
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.colors.gray[600]};
  margin: 0 0 ${({ theme }) => theme.spacing[6]};
  line-height: ${({ theme }) => theme.lineHeight.normal};
`

const Actions = styled.div`
  display: flex;
  justify-content: flex-end;
  gap: ${({ theme }) => theme.spacing[3]};
`

export function AlertDialog({
  open,
  onOpenChange,
  title,
  description,
  confirmLabel = 'Confirm',
  onConfirm,
  children,
}: AlertDialogProps) {
  return (
    <RadixAlertDialog.Root open={open} onOpenChange={onOpenChange}>
      {children && (
        <RadixAlertDialog.Trigger asChild>
          {children}
        </RadixAlertDialog.Trigger>
      )}
      <RadixAlertDialog.Portal>
        <Overlay />
        <Content>
          <Title>{title}</Title>
          <Description>{description}</Description>
          <Actions>
            <RadixAlertDialog.Cancel asChild>
              <Button $variant="secondary">Cancel</Button>
            </RadixAlertDialog.Cancel>
            <RadixAlertDialog.Action asChild>
              <Button $variant="danger" onClick={onConfirm}>{confirmLabel}</Button>
            </RadixAlertDialog.Action>
          </Actions>
        </Content>
      </RadixAlertDialog.Portal>
    </RadixAlertDialog.Root>
  )
}
