import { RouterProvider } from 'react-router-dom'
import { QueryClientProvider } from '@tanstack/react-query'
import { ThemeProvider } from 'styled-components'
import { router } from '@/lib/router'
import { queryClient } from '@/lib/queryClient'
import { theme } from '@/theme'
import { AuthProvider } from '@/features/auth/AuthContext'
import { OrganizationProvider } from '@/features/organizations/OrganizationContext'

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <AuthProvider>
          <OrganizationProvider>
            <RouterProvider router={router} />
          </OrganizationProvider>
        </AuthProvider>
      </ThemeProvider>
    </QueryClientProvider>
  )
}
