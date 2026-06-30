import { createBrowserRouter, Navigate } from 'react-router-dom'
import { AuthenticatedLayout } from '@/layouts/AuthenticatedLayout'
import { UnauthenticatedLayout } from '@/layouts/UnauthenticatedLayout'
import { LoginPage } from '@/pages/LoginPage'
import { DashboardPage } from '@/pages/DashboardPage'
import { IntegrationListPage } from '@/pages/IntegrationListPage'
import { IntegrationCreatePage } from '@/pages/IntegrationCreatePage'
import { IntegrationEditPage } from '@/pages/IntegrationEditPage'
import { IntegrationDetailPage } from '@/pages/IntegrationDetailPage'
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
      { path: '/integrations', element: <IntegrationListPage /> },
      { path: '/integrations/new', element: <IntegrationCreatePage /> },
      { path: '/integrations/:integrationId', element: <IntegrationDetailPage /> },
      { path: '/integrations/:integrationId/edit', element: <IntegrationEditPage /> },
    ],
  },
  {
    path: '*',
    element: <Navigate to="/dashboard" replace />,
  },
])
