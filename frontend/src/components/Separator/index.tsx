import * as RadixSeparator from '@radix-ui/react-separator'
import styled from 'styled-components'

export const Separator = styled(RadixSeparator.Root)`
  background-color: ${({ theme }) => theme.colors.gray[200]};

  &[data-orientation='horizontal'] {
    height: 1px;
    width: 100%;
    margin: ${({ theme }) => `${theme.spacing[4]} 0`};
  }

  &[data-orientation='vertical'] {
    height: 100%;
    width: 1px;
    margin: ${({ theme }) => `0 ${theme.spacing[4]}`};
  }
`
