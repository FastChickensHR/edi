import * as RadixLabel from '@radix-ui/react-label'
import styled from 'styled-components'

export const Label = styled(RadixLabel.Root)`
  font-size: ${({ theme }) => theme.fontSize.sm};
  font-weight: ${({ theme }) => theme.fontWeight.medium};
  color: ${({ theme }) => theme.colors.gray[700]};
  cursor: default;
`
