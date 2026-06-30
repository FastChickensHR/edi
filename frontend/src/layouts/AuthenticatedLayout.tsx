import { Outlet, NavLink } from 'react-router-dom'
import styled from 'styled-components'
import {
  Squares2X2Icon,
  ArrowsRightLeftIcon,
  BuildingOfficeIcon,
  UsersIcon,
} from '@heroicons/react/24/outline'
import { useAuth } from '@/hooks/useAuth'
import { useOrganization } from '@/hooks/useOrganization'

const Shell = styled.div`
  display: flex;
  height: 100vh;
  overflow: hidden;
  background-color: ${({ theme }) => theme.colors.gray[100]};
`

const Sidebar = styled.nav`
  width: 16rem;
  display: flex;
  flex-direction: column;
  background-color: ${({ theme }) => theme.colors.gray[900]};
  flex-shrink: 0;
`

const SidebarHeader = styled.div`
  padding: ${({ theme }) => theme.spacing[6]};
  border-bottom: 1px solid ${({ theme }) => theme.colors.gray[700]};
`

const Logo = styled.span`
  font-size: ${({ theme }) => theme.fontSize.lg};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  color: ${({ theme }) => theme.colors.white};
`

const OrgName = styled.span`
  display: block;
  font-size: ${({ theme }) => theme.fontSize.xs};
  color: ${({ theme }) => theme.colors.gray[400]};
  margin-top: ${({ theme }) => theme.spacing[1]};
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`

const NavList = styled.ul`
  list-style: none;
  margin: 0;
  padding: ${({ theme }) => theme.spacing[4]} 0;
  flex: 1;
`

const NavItem = styled.li``

const StyledNavLink = styled(NavLink)`
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0.625rem ${({ theme }) => theme.spacing[6]};
  font-size: ${({ theme }) => theme.fontSize.sm};
  text-decoration: none;
  color: ${({ theme }) => theme.colors.gray[300]};
  transition: background-color 150ms ease;

  &:hover {
    background-color: ${({ theme }) => theme.colors.gray[800]};
    color: ${({ theme }) => theme.colors.white};
  }

  &.active {
    background-color: ${({ theme }) => theme.colors.gray[800]};
    color: ${({ theme }) => theme.colors.white};
  }
`

const NavIconWrapper = styled.span`
  width: 1.25rem;
  height: 1.25rem;
  flex-shrink: 0;
  display: flex;
  align-items: center;
`

const SidebarFooter = styled.div`
  padding: ${({ theme }) => theme.spacing[4]} ${({ theme }) => theme.spacing[6]};
  border-top: 1px solid ${({ theme }) => theme.colors.gray[700]};
`

const UserEmail = styled.span`
  display: block;
  font-size: ${({ theme }) => theme.fontSize.xs};
  color: ${({ theme }) => theme.colors.gray[400]};
  margin-bottom: ${({ theme }) => theme.spacing[2]};
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`

const LogoutButton = styled.button`
  font-size: ${({ theme }) => theme.fontSize.xs};
  color: ${({ theme }) => theme.colors.gray[400]};
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;

  &:hover {
    color: ${({ theme }) => theme.colors.white};
  }
`

const Main = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
`

const TopBar = styled.header`
  height: 4rem;
  display: flex;
  align-items: center;
  padding: 0 ${({ theme }) => theme.spacing[6]};
  background-color: ${({ theme }) => theme.colors.white};
  border-bottom: 1px solid ${({ theme }) => theme.colors.gray[200]};
  flex-shrink: 0;
`

const PageTitle = styled.h1`
  font-size: ${({ theme }) => theme.fontSize.lg};
  font-weight: ${({ theme }) => theme.fontWeight.semibold};
  color: ${({ theme }) => theme.colors.gray[900]};
  margin: 0;
`

const Content = styled.main`
  flex: 1;
  overflow-y: auto;
  padding: ${({ theme }) => theme.spacing[6]};
`

const navItems = [
  { to: '/dashboard', label: 'Dashboard', Icon: Squares2X2Icon },
  { to: '/integrations', label: 'Integrations', Icon: ArrowsRightLeftIcon },
  { to: '/organizations', label: 'Organizations', Icon: BuildingOfficeIcon },
  { to: '/members', label: 'Members', Icon: UsersIcon },
]

interface AuthenticatedLayoutProps {
  title?: string
}

export function AuthenticatedLayout({ title }: AuthenticatedLayoutProps) {
  const { user, logout } = useAuth()
  const { activeOrganization } = useOrganization()

  return (
    <Shell>
      <Sidebar>
        <SidebarHeader>
          <Logo>fastChickens</Logo>
          {activeOrganization && <OrgName>{activeOrganization.name}</OrgName>}
        </SidebarHeader>
        <NavList>
          {navItems.map(({ to, label, Icon }) => (
            <NavItem key={to}>
              <StyledNavLink to={to}>
                <NavIconWrapper>
                  <Icon width={20} height={20} />
                </NavIconWrapper>
                {label}
              </StyledNavLink>
            </NavItem>
          ))}
        </NavList>
        <SidebarFooter>
          <UserEmail>{user?.email}</UserEmail>
          <LogoutButton onClick={logout}>Sign out</LogoutButton>
        </SidebarFooter>
      </Sidebar>
      <Main>
        {title && (
          <TopBar>
            <PageTitle>{title}</PageTitle>
          </TopBar>
        )}
        <Content>
          <Outlet />
        </Content>
      </Main>
    </Shell>
  )
}
