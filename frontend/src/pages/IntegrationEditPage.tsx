import { useNavigate, useParams } from 'react-router-dom'
import styled from 'styled-components'
import { useAuth } from '@/hooks/useAuth'
import { useOrganization } from '@/hooks/useOrganization'
import { toast } from '@/components/Toast'
import { IntegrationForm, integrationResponseToFormValues } from '@/features/integrations/components/IntegrationForm'
import {
  useIntegration,
  useUpdateIntegration,
} from '@/features/integrations/hooks/useIntegrations'

const PageHeader = styled.div`
  margin-bottom: ${({ theme }) => theme.spacing[6]};
`

const PageTitle = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['2xl']};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  color: ${({ theme }) => theme.colors.gray[900]};
  margin: 0;
`

export function IntegrationEditPage() {
  const { integrationId } = useParams<{ integrationId: string }>()
  const navigate = useNavigate()
  const { user } = useAuth()
  const { activeOrganization } = useOrganization()
  const { data: integration, isLoading } = useIntegration(integrationId!)
  const updateIntegration = useUpdateIntegration(integrationId!)

  if (isLoading) {
    return <p style={{ color: 'var(--color-gray-500)', fontSize: '0.875rem' }}>Loading…</p>
  }

  if (!integration || !user || !activeOrganization) return null

  return (
    <>
      <PageHeader>
        <PageTitle>Edit Integration</PageTitle>
      </PageHeader>
      <IntegrationForm
        defaultValues={integrationResponseToFormValues(integration)}
        organizationId={activeOrganization.id}
        createdByUserId={user.id}
        submitLabel="Save Changes"
        onSubmit={async (data) => {
          await updateIntegration.mutateAsync(data)
          toast.success('Integration updated')
          navigate(`/integrations/${integrationId}`)
        }}
        onCancel={() => navigate(`/integrations/${integrationId}`)}
      />
    </>
  )
}
