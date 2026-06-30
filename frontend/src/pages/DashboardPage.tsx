import styled from 'styled-components'
import { useAuth } from '@/hooks/useAuth'

const Greeting = styled.p`
  font-size: ${({ theme }) => theme.fontSize.base};
  color: ${({ theme }) => theme.colors.gray[700]};
`

export function DashboardPage() {
  const { user } = useAuth()

  return (
    <div>
      <Greeting>Welcome back, {user?.email}</Greeting>
    </div>
  )
}
