import styled from 'styled-components'

export const Input = styled.input<{ $hasError?: boolean }>`
  width: 100%;
  padding: ${({ theme }) => `${theme.spacing[2]} ${theme.spacing[3]}`};
  border: 1px solid
    ${({ theme, $hasError }) => ($hasError ? theme.colors.red[500] : theme.colors.gray[300])};
  border-radius: ${({ theme }) => theme.borderRadius.md};
  font-size: ${({ theme }) => theme.fontSize.sm};
  color: ${({ theme }) => theme.colors.gray[900]};
  background-color: ${({ theme }) => theme.colors.white};
  outline: none;
  box-sizing: border-box;

  &:focus {
    border-color: ${({ theme }) => theme.colors.blue[500]};
    box-shadow: 0 0 0 3px ${({ theme }) => theme.colors.blue[100]};
  }

  &:disabled {
    background-color: ${({ theme }) => theme.colors.gray[50]};
    color: ${({ theme }) => theme.colors.gray[500]};
    cursor: not-allowed;
  }

  &::placeholder {
    color: ${({ theme }) => theme.colors.gray[400]};
  }
`

export const ErrorText = styled.p`
  font-size: ${({ theme }) => theme.fontSize.xs};
  color: ${({ theme }) => theme.colors.red[600]};
  margin: 0;
`

export const Field = styled.div`
  display: flex;
  flex-direction: column;
  gap: ${({ theme }) => theme.spacing[1]};
`
