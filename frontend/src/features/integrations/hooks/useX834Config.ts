import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { http } from '@/lib/http'

export interface X834ConfigResponse {
  integrationId: string
  senderID: string
  receiverID: string
  elementSeparator: string
  policyNumber: string
  memberIdQualifier: string
  referenceIdentification: string
  masterPolicyNumber: string
  planSponsorName: string
  payerName: string
  payerIdentification: string
  createdAt: string
}

export interface X834ConfigRequest {
  senderID: string
  receiverID: string
  elementSeparator: string
  policyNumber: string
  memberIdQualifier: string
  referenceIdentification: string
  masterPolicyNumber: string
  planSponsorName: string
  payerName: string
  payerIdentification: string
}

const configKey = (integrationId: string) => ['integrations', integrationId, 'config'] as const

export function useX834Config(integrationId: string) {
  return useQuery<X834ConfigResponse>({
    queryKey: configKey(integrationId),
    queryFn: () => http.get(`/api/integrations/${integrationId}/config`),
    enabled: !!integrationId,
    retry: (failureCount, error) => {
      // Don't retry 404 — config simply doesn't exist yet
      if (error.message === '404') return false
      return failureCount < 3
    },
  })
}

export function useCreateX834Config(integrationId: string) {
  const queryClient = useQueryClient()
  return useMutation<X834ConfigResponse, Error, X834ConfigRequest>({
    mutationFn: (data) => http.post(`/api/integrations/${integrationId}/config`, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: configKey(integrationId) })
    },
  })
}

export function useUpdateX834Config(integrationId: string) {
  const queryClient = useQueryClient()
  return useMutation<X834ConfigResponse, Error, X834ConfigRequest>({
    mutationFn: (data) => http.put(`/api/integrations/${integrationId}/config`, data),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: configKey(integrationId) })
    },
  })
}

export function useDeleteX834Config(integrationId: string) {
  const queryClient = useQueryClient()
  return useMutation<void, Error, void>({
    mutationFn: () => http.delete(`/api/integrations/${integrationId}/config`),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: configKey(integrationId) })
    },
  })
}
