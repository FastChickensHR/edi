import { createBrowserRouter, Navigate } from 'react-router-dom'
import { AuthenticatedLayout } from '@/layouts/AuthenticatedLayout'
import { UnauthenticatedLayout } from '@/layouts/UnauthenticatedLayout'
import { LoginPage } from '@/pages/LoginPage'
import { DashboardPage } from '@/pages/DashboardPage'
import { RequireAuth } from '@/features/auth/RequireAuth'

export const router = createBrowserRouter([
  {
    element: <UnauthenticatedLayout />,
    children: [
      { path: '/login', element: <LoginPage /> },
    ],
  },
  {
    element: (
      <RequireAuth>
        <AuthenticatedLayout />
      </RequireAuth>
    ),
    children: [
      { index: true, element: <Navigate to="/dashboard" replace /> },
      { path: '/dashboard', element: <DashboardPage /> },
      // Feature routes will be added here as features are built
      // { path: '/integrations', element: <IntegrationsPage /> },
      // { path: '/integrations/:integrationId', element: <IntegrationDetailPage /> },
      // { path: '/organizations', element: <OrganizationsPage /> },
    ],
  },
  {
    path: '*',
    element: <Navigate to="/dashboard" replace />,
  },
])
