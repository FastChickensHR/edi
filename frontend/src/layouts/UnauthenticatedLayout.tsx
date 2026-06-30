import { Outlet } from 'react-router-dom'
import styled from 'styled-components'

const Root = styled.div`
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: ${({ theme }) => theme.colors.gray[50]};
`

const Card = styled.main`
  width: 100%;
  max-width: 28rem;
  padding: ${({ theme }) => theme.spacing[8]};
  background-color: ${({ theme }) => theme.colors.white};
  border-radius: ${({ theme }) => theme.borderRadius.xl};
  box-shadow: ${({ theme }) => theme.boxShadow.md};
`

export function UnauthenticatedLayout() {
  return (
    <Root>
      <Card>
        <Outlet />
      </Card>
    </Root>
  )
}
