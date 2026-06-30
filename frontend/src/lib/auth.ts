import { jwtDecode } from 'jwt-decode'
import type { MemberRole } from '@/types'

interface JwtPayload {
  role?: string
  sub?: string
  exp?: number
}

export function decodeJwtRole(token: string): MemberRole | null {
  try {
    const payload = jwtDecode<JwtPayload>(token)
    const role = payload.role
    if (role === 'OWNER' || role === 'ADMIN' || role === 'EDITOR' || role === 'VIEWER') {
      return role
    }
    return null
  } catch {
    return null
  }
}
