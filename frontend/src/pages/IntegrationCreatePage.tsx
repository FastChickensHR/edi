import { useNavigate } from 'react-router-dom'
import styled from 'styled-components'
import { useAuth } from '@/hooks/useAuth'
import { useOrganization } from '@/hooks/useOrganization'
import { toast } from '@/components/Toast'
import { IntegrationForm } from '@/features/integrations/components/IntegrationForm'
import { useCreateIntegration } from '@/features/integrations/hooks/useIntegrations'

const PageHeader = styled.div`
  margin-bottom: ${({ theme }) => theme.spacing[6]};
`

const PageTitle = styled.h1`
  font-size: ${({ theme }) => theme.fontSize['2xl']};
  font-weight: ${({ theme }) => theme.fontWeight.bold};
  color: ${({ theme }) => theme.colors.gray[900]};
  margin: 0;
`

export function IntegrationCreatePage() {
  const navigate = useNavigate()
  const { user } = useAuth()
  const { activeOrganization } = useOrganization()
  const createIntegration = useCreateIntegration()

  if (!user || !activeOrganization) return null

  return (
    <>
      <PageHeader>
        <PageTitle>New Integration</PageTitle>
      </PageHeader>
      <IntegrationForm
        organizationId={activeOrganization.id}
        createdByUserId={user.id}
        submitLabel="Create Integration"
        onSubmit={async (data) => {
          const result = await createIntegration.mutateAsync(data)
          toast.success('Integration created')
          navigate(`/integrations/${result.integrationId}`)
        }}
        onCancel={() => navigate('/integrations')}
      />
    </>
  )
}
