import * as RadixSelect from '@radix-ui/react-select'
import { ChevronDownIcon, ChevronUpIcon, CheckIcon } from '@heroicons/react/24/outline'
import styled from 'styled-components'

export interface SelectOption {
  value: string
  label: string
}

interface SelectProps {
  value?: string
  onValueChange?: (value: string) => void
  options: SelectOption[]
  placeholder?: string
  disabled?: boolean
  $hasError?: boolean
}

const Trigger = styled(RadixSelect.Trigger)<{ $hasError?: boolean }>`
  display: inline-flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: ${({ theme }) => `${theme.spacing[2]} ${theme.spacing[3]}`};
  border: 1px solid
    ${({ theme, $hasError }) => ($hasError ? theme.colors.red[500] : theme.colors.gray[300])};
  border-radius: ${({ theme }) => theme.borderRadius.md};
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.colors.gray[900]};
  background-color: ${({ theme }) => theme.colors.white};
  cursor: pointer;
  outline: none;
  gap: ${({ theme }) => theme.spacing[2]};
  box-sizing: border-box;

  &:focus {
    border-color: ${({ theme }) => theme.colors.blue[500]};
    box-shadow: 0 0 0 3px ${({ theme }) => theme.colors.blue[100]};
  }

  &[data-disabled] {
    background-color: ${({ theme }) => theme.colors.gray[50]};
    color: ${({ theme }) => theme.colors.gray[500]};
    cursor: not-allowed;
  }

  &[data-placeholder] {
    color: ${({ theme }) => theme.colors.gray[400]};
  }
`

const Content = styled(RadixSelect.Content)`
  overflow: hidden;
  background-color: ${({ theme }) => theme.colors.white};
  border: 1px solid ${({ theme }) => theme.colors.gray[200]};
  border-radius: ${({ theme }) => theme.borderRadius.md};
  box-shadow: ${({ theme }) => theme.boxShadow.md};
  z-index: 50;
`

const Viewport = styled(RadixSelect.Viewport)`
  padding: ${({ theme }) => theme.spacing[1]};
`

const Item = styled(RadixSelect.Item)`
  display: flex;
  align-items: center;
  padding: ${({ theme }) => `${theme.spacing[2]} ${theme.spacing[8]} ${theme.spacing[2]} ${theme.spacing[3]}`};
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.colors.gray[900]};
  border-radius: ${({ theme }) => theme.borderRadius.sm};
  cursor: pointer;
  outline: none;
  position: relative;
  user-select: none;

  &[data-highlighted] {
    background-color: ${({ theme }) => theme.colors.blue[50]};
    color: ${({ theme }) => theme.colors.blue[900]};
  }
`

const ItemIndicator = styled(RadixSelect.ItemIndicator)`
  position: absolute;
  right: ${({ theme }) => theme.spacing[2]};
  display: inline-flex;
  align-items: center;
`

const ScrollButton = styled(RadixSelect.ScrollUpButton)`
  display: flex;
  align-items: center;
  justify-content: center;
  height: ${({ theme }) => theme.spacing[6]};
  color: ${({ theme }) => theme.colors.gray[500]};
  cursor: default;
`

export function Select({ value, onValueChange, options, placeholder, disabled, $hasError }: SelectProps) {
  return (
    <RadixSelect.Root value={value} onValueChange={onValueChange} disabled={disabled}>
      <Trigger $hasError={$hasError}>
        <RadixSelect.Value placeholder={placeholder ?? 'Select…'} />
        <RadixSelect.Icon>
          <ChevronDownIcon style={{ width: '1rem', height: '1rem' }} />
        </RadixSelect.Icon>
      </Trigger>
      <RadixSelect.Portal>
        <Content position="popper" sideOffset={4}>
          <ScrollButton as={RadixSelect.ScrollUpButton}>
            <ChevronUpIcon style={{ width: '1rem', height: '1rem' }} />
          </ScrollButton>
          <Viewport>
            {options.map((opt) => (
              <Item key={opt.value} value={opt.value}>
                <RadixSelect.ItemText>{opt.label}</RadixSelect.ItemText>
                <ItemIndicator>
                  <CheckIcon style={{ width: '0.875rem', height: '0.875rem' }} />
                </ItemIndicator>
              </Item>
            ))}
          </Viewport>
          <ScrollButton as={RadixSelect.ScrollDownButton}>
            <ChevronDownIcon style={{ width: '1rem', height: '1rem' }} />
          </ScrollButton>
        </Content>
      </RadixSelect.Portal>
    </RadixSelect.Root>
  )
}
