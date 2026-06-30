import { RouterProvider } from 'react-router-dom'
import { QueryClientProvider } from '@tanstack/react-query'
import { ThemeProvider } from 'styled-components'
import { router } from '@/lib/router'
import { queryClient } from '@/lib/queryClient'
import { theme } from '@/theme'
import { AuthProvider } from '@/features/auth/AuthContext'
import { OrganizationProvider } from '@/features/organizations/OrganizationContext'
import { Toaster } from '@/components/Toast'

export default function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <ThemeProvider theme={theme}>
        <AuthProvider>
          <OrganizationProvider>
            <RouterProvider router={router} />
            <Toaster position="bottom-right" richColors />
          </OrganizationProvider>
        </AuthProvider>
      </ThemeProvider>
    </QueryClientProvider>
  )
}
