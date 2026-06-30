import { createContext, useCallback, useEffect, useRef, useState, type ReactNode } from 'react'
import { http, setAccessToken } from '@/lib/http'
import { decodeJwtRole } from '@/lib/auth'
import { queryClient } from '@/lib/queryClient'
import type { User, LoginRequest, MemberRole } from '@/types'

interface AuthState {
  user: User | null
  memberRole: MemberRole | null
  isLoading: boolean
  isAuthenticated: boolean
  login: (credentials: LoginRequest) => Promise<void>
  logout: () => Promise<void>
}

// Backend login response — refreshToken is in an httpOnly cookie, not here
interface LoginResponse {
  accessToken: string
  expiresIn: number
  user: User
}

export const AuthContext = createContext<AuthState | null>(null)

export function AuthProvider({ children }: { children: ReactNode }) {
  const [user, setUser] = useState<User | null>(null)
  const [memberRole, setMemberRole] = useState<MemberRole | null>(null)
  const [isLoading, setIsLoading] = useState(true)
  const initialized = useRef(false)

  const logout = useCallback(async () => {
    try {
      // POST /api/auth/revoke — cookie is sent automatically, clears the httpOnly cookie
      await http.post('/api/auth/revoke')
    } catch {
      // best-effort
    } finally {
      setAccessToken(null)
      setUser(null)
      setMemberRole(null)
      queryClient.clear()
    }
  }, [])

  // Restore session on mount via the httpOnly refresh cookie
  useEffect(() => {
    if (initialized.current) return
    initialized.current = true

    http
      .post<LoginResponse>('/api/auth/refresh')
      .then(({ accessToken, user }) => {
        setAccessToken(accessToken)
        setUser(user)
        setMemberRole(decodeJwtRole(accessToken))
      })
      .catch(() => {
        setAccessToken(null)
        setUser(null)
        setMemberRole(null)
      })
      .finally(() => setIsLoading(false))
  }, [])

  // Global 401 handler
  useEffect(() => {
    const handler = () => logout()
    window.addEventListener('auth:unauthorized', handler)
    return () => window.removeEventListener('auth:unauthorized', handler)
  }, [logout])

  const login = useCallback(async (credentials: LoginRequest) => {
    const { accessToken, user } = await http.post<LoginResponse>('/api/auth/login', credentials)
    setAccessToken(accessToken)
    setUser(user)
    setMemberRole(decodeJwtRole(accessToken))
  }, [])

  return (
    <AuthContext.Provider value={{ user, memberRole, isLoading, isAuthenticated: user !== null, login, logout }}>
      {children}
    </AuthContext.Provider>
  )
}
