import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { http } from '@/lib/http'

export interface IntegrationResponse {
  integrationId: string
  name: string
  organizationId: string
  createdByUserId: string
  fromSystem: string
  toSystem: string
  format: string
  direction: string
  createdAt: string
}

export interface IntegrationRequest {
  name: string
  organizationId: string
  createdByUserId: string
  fromSystem: string
  toSystem: string
  format: string
}

const INTEGRATIONS_KEY = 'integrations'

export function useIntegrations() {
  return useQuery<IntegrationResponse[]>({
    queryKey: [INTEGRATIONS_KEY],
    queryFn: () => http.get('/api/integrations'),
  })
}

export function useIntegration(id: string) {
  return useQuery<IntegrationResponse>({
    queryKey: [INTEGRATIONS_KEY, id],
    queryFn: () => http.get(`/api/integrations/${id}`),
    enabled: !!id,
  })
}

export function useCreateIntegration() {
  const queryClient = useQueryClient()
  return useMutation<IntegrationResponse, Error, IntegrationRequest>({
    mutationFn: (data) => http.post('/api/integrations', data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [INTEGRATIONS_KEY] })
    },
  })
}

export function useUpdateIntegration(id: string) {
  const queryClient = useQueryClient()
  return useMutation<IntegrationResponse, Error, IntegrationRequest>({
    mutationFn: (data) => http.put(`/api/integrations/${id}`, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [INTEGRATIONS_KEY] })
    },
  })
}

export function useDeleteIntegration() {
  const queryClient = useQueryClient()
  return useMutation<void, Error, string>({
    mutationFn: (id) => http.delete(`/api/integrations/${id}`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [INTEGRATIONS_KEY] })
    },
  })
}
